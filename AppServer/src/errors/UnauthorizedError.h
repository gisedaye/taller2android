//
// Created by fedefarina on 17/04/16.
//

#ifndef APPSERVER_UNAUTHORIZEDERROR_H
#define APPSERVER_UNAUTHORIZEDERROR_H

#include "Error.h"

class UnauthorizedError : public Error {

public:
    UnauthorizedError() {
        this->message = "Bad credentials";
    }

    virtual int getCode() {
        return 5;
    }

    virtual ~UnauthorizedError() {

    }
};


#endif //APPSERVER_UNAUTHORIZEDERROR_H
