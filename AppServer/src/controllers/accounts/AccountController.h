//
// Created by fedefarina on 26/03/16.
//

#ifndef APPSERVER_ACCOUNTCONTROLLER_H
#define APPSERVER_ACCOUNTCONTROLLER_H

#include <sha256/sha256.h>
#include <mongoose/Server.h>
#include <mongoose/JsonResponse.h>
#include "../BaseController.h"
#include "../../model/Account.h"
#include "../../errors/BadJsonError.h"
#include "../../errors/EmptyParamError.h"

using namespace std;
using namespace Mongoose;

class AccountController : public BaseController {
private:
public:
    virtual bool requireAuthentication(string method, string url) override;

    string generateUserId(const string &username) const;

    string generateToken(const string &username, const string &password) const;

    string encodePassword(const string &password) const;
public:

    AccountController();

    void setup();

    void signup(Request &request, JsonResponse &response);

    void login(Request &request, JsonResponse &response);

    void like(Request &request, JsonResponse &response);

    void dislike(Request &request, JsonResponse &response);

    void validateAccount(string username, string password, vector<Error *> &errors);

    virtual ~AccountController();
};


#endif //APPSERVER_ACCOUNTCONTROLLER_H
