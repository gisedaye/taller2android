#ifndef APPSERVER_USERNAMEALREADYINUSE_H
#define APPSERVER_USERNAMEALREADYINUSE_H

#include "Error.h"

class UsernameAlreadyInUseError : public Error {

public:
    UsernameAlreadyInUseError() {
        this->message = "Username already in use";
    }

    virtual int getCode() {
        return 4;
    }
};

#endif //APPSERVER_USERNAMEALREADYINUSE_H
