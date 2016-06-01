//
// Created by fedefarina on 26/03/16.
//

#include <regex>
#include "BaseController.h"
#include "../utils/FileLogger.h"
#include "../model/AccessToken.h"
#include "../errors/UnauthorizedError.h"


BaseController::BaseController() {
    routeParams = new map<string, string>();
}

bool BaseController::handles(string method, string url) {

    bool handle = false;
    string incomingKey = method + ":" + url;

    map<string, RequestHandlerBase *>::iterator it;
    for (it = routes.begin(); it != routes.end(); it++) {
        string replacedKey = replaceRouteParams(it->first);
        if (regex_match(incomingKey, regex(replacedKey))) {
            handle = true;
            break;
        }
    }

    return handle;
}


Response *BaseController::process(Request &request) {

    string currentRequest = request.getMethod() + ":" + request.getUrl();
    FileLogger::info("Processing request: " + currentRequest);

    Response *response = NULL;

    map<string, RequestHandlerBase *>::iterator it;
    for (it = routes.begin(); it != routes.end(); it++) {
        string key = it->first;


        string regexKey = replaceRouteParams(key);

        if (regex_match(currentRequest, regex(regexKey))) {

            //Only search for route params in regex keys
            if (regexKey.find(".*") != string::npos) {
                parseRouteParams(key, currentRequest);
            }

            response = it->second->process(request);
            break;
        }


    }

    return response;
}

void BaseController::parseRouteParams(const string &key, const string &currentRequest) const {
    routeParams->clear();
    //Get map key
    unsigned long firstPos = key.find("{");
    unsigned long secondPos = key.find("}");

    string mapKey = key.substr(firstPos + 1, secondPos - firstPos - 1);

    //Get map value
    string requestTail = currentRequest.substr(firstPos);
    unsigned long incomingRequestValueEnd = requestTail.find("/");
    string value = currentRequest.substr(firstPos, incomingRequestValueEnd);

    routeParams->insert(std::pair<string, string>(mapKey, value));
}

//@Fede Due to a mongoose cpp "double url check" issue we have to run this method twice, so we validate that we are in first one
string BaseController::replaceRouteParams(string key) const {

    string replacedKey = key;
    unsigned long firstPos = replacedKey.find("{");
    unsigned long secondPos = replacedKey.find("}");

    //Replace {param} with .*
    while (firstPos != string::npos && secondPos != string::npos) {
        replacedKey = replacedKey.replace(firstPos, secondPos - firstPos + 1, ".*");
        firstPos = replacedKey.find("{");
        secondPos = replacedKey.find("}");
    }

    return replacedKey;
}

bool BaseController::tokenAuthenticate(Request &request) {
    string tokenHeader = request.getHeaderKeyValue("Authorization");

    AccessToken token;
    token.setToken(tokenHeader);

    try {
        if (token.fetch()) {
            string username = token.getUsername();
            request.setUsername(username);
            return true;
        }
    } catch (std::domain_error &e) {
        //Username not found
        FileLogger::error((string(e.what())));
        return false;
    }
}


JsonResponse &BaseController::sendBadJsonError(JsonResponse &response) {
    vector<Error *> errors;
    BadJsonError *badJsonError = new BadJsonError();
    errors.push_back(badJsonError);
    sendErrors(response, errors, 400);
    return response;
}


JsonResponse &BaseController::sendUnauthorizedResponse(JsonResponse &response) {
    vector<Error *> errors;
    UnauthorizedError *unauthorizedError = new UnauthorizedError();
    errors.push_back(unauthorizedError);
    sendErrors(response, errors, 401);
    return response;
}


/* Errors format
{
    "errors": [
        {
            "code": errorCode,
            "title": "error"
        }
    ]
}
*/
void BaseController::sendErrors(JsonResponse &response, vector<Error *> &errors, int responseCode) {

    response.setCode(responseCode);
    setHeaders(response);
    string message;
    int code;
    for (unsigned int i = 0; i < errors.size(); i++) {
        Error *error = errors[i];
        message = error->getMessage();
        code = error->getCode();
        response["errors"][i]["code"] = code;
        response["errors"][i]["message"] = message;
        delete (error);
    }

    ostringstream s;
    s << "Error processing request with code: " << responseCode << endl;
    s << "Response ->" << response.toStyledString();
    FileLogger::error(s.str());


    errors.clear();
}


/* Responses format
{
  "data": {
    ...
  }
}
*/
void BaseController::sendResult(JsonResponse &response, JsonResponse &responseBody, int responseCode) {
    FileLogger::info(response.asString());
    response.setCode(responseCode);
    response["data"] = responseBody;
    setHeaders(response);
}

void BaseController::setHeaders(JsonResponse &response) {
    response.setHeader("Content-Type", "application/json; charset=utf-8");
}


bool BaseController::requireAuthentication(string method, string url) {
    return true;
}

BaseController::~BaseController() {
    delete routeParams;
}





















