//
// Created by fedefarina on 26/03/16.
//

#include "AccountController.h"
#include "../../errors/UsernameAlreadyInUseError.h"
#include "../../model/AccessToken.h"
#include "../../errors/UnauthorizedError.h"
#include "../../utils/FileLogger.h"

AccountController::AccountController() {

}

void AccountController::login(Request &request, JsonResponse &response) {

    vector<Error *> errors;

    Json::Value body = request.getBody();

    string username = body.get("username", "").asString();
    string password = body.get("password", "").asString();
    validateAccount(username, password, errors);

    if (!errors.empty()) {
        return sendErrors(response, errors, 400);
    }

    Account account(generateUserId(username));
    JsonResponse responseBody;

    if (account.fetch()) {
        if (account.getPassword() != encodePassword(password)) {
            errors.push_back(new UnauthorizedError());
        } else {
            responseBody["message"] = "Successful login.";
            const string &accessToken = generateToken(username, password);
            AccessToken token;
            token.setToken(accessToken);
            token.setUsername(generateUserId(username));
            token.save();
            responseBody["accessToken"] = accessToken;
        }
    } else {
        errors.push_back(new UnauthorizedError());
    }

    if (errors.empty()) {
        sendResult(response, responseBody, HTTP_OK);
    } else {
        sendErrors(response, errors, 401);
    }
}

string AccountController::encodePassword(const string &password) const {
    return sha256(password);
}

string AccountController::generateToken(const string &username, const string &password) const {
    return sha256(username + password);
}

void AccountController::signup(Request &request, JsonResponse &response) {

    vector<Error *> errors;

    Json::Value body = request.getBody();

    string username = body.get("username", "").asString();
    string password = body.get("password", "").asString();
    validateAccount(username, password, errors);

    if (!errors.empty()) {
        return sendErrors(response, errors, 400);
    }

    JsonResponse jsonResponse;
    Account account(generateUserId(username));

    if (!account.fetch()) {
        const string &encodedPassword = encodePassword(password);
        account.setPassword(encodedPassword);
        account.setUsername(username);
        account.save();
        jsonResponse["message"] = "Successful signup";
    } else {
        errors.push_back(new UsernameAlreadyInUseError());
    }

    if (errors.empty()) {
        sendResult(response, jsonResponse, HTTP_OK);
    } else {
        sendErrors(response, errors, 400);
    }
}

string AccountController::generateUserId(const string &username) const {
    cout << sha256(username) << endl;
    return sha256(username);
}

void AccountController::like(Request &request, JsonResponse &response) {
    vector<Error *> errors;

    if (tokenAuthenticate(request)) {
        string keptAccount = routeParams->at("username");
        Account account = request.getUser();
        account.addKeepAccount(keptAccount);
        account.save();
        JsonResponse responseBody;
        responseBody["message"] = "Like successful";
        sendResult(response, responseBody, HTTP_OK);
    } else {
        errors.push_back(new UnauthorizedError());
        sendErrors(response, errors, 401);
    }
}

void AccountController::dislike(Request &request, JsonResponse &response) {
    vector<Error *> errors;

    if (tokenAuthenticate(request)) {
        string tossedAccount = routeParams->at("id");
        Account account = request.getUser();
        account.addTossAccount(tossedAccount);
        account.save();
        JsonResponse responseBody;
        responseBody["message"] = "Dislike successful";
        sendResult(response, responseBody, HTTP_OK);
    } else {
        errors.push_back(new UnauthorizedError());
        sendErrors(response, errors, 401);
    }
}

void AccountController::validateAccount(string username, string password,
                                        vector<Error *> &errors) {
    if (username.empty()) {
        EmptyParamError *emptyUserError = new EmptyParamError();
        emptyUserError->setMessage("Empty username");
        errors.push_back(emptyUserError);
    }

    if (password.empty()) {
        EmptyParamError *emptyPassword = new EmptyParamError();
        emptyPassword->setMessage("Empty password");
        errors.push_back(emptyPassword);
    }
}

void AccountController::setup() {
    setPrefix("/api/accounts");
    addRouteResponse("POST", "/signup", AccountController, signup, JsonResponse);
    addRouteResponse("POST", "/login", AccountController, login, JsonResponse);
    addRouteResponse("PUT", "/{username}/like", AccountController, like, JsonResponse);
    addRouteResponse("PUT", "/{username}/dislike", AccountController, dislike, JsonResponse);
}

bool AccountController::requireAuthentication(string method, string url) {
    if ((!method.compare("POST") && !url.compare(getPrefix() + "/login"))
        || (!method.compare("POST") && !url.compare(getPrefix() + "/signup"))) {
        return false;
    }
}

AccountController::~AccountController() {

}
