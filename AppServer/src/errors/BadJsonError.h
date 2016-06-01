//
// Created by fedefarina on 26/03/16.
//

#ifndef APPSERVER_BADJSONERROR_H
#define APPSERVER_BADJSONERROR_H


#include "Error.h"

class BadJsonError : public Error {


public:
    BadJsonError() {
        this->message = "Bad Json";
    }

    virtual int getCode() {
        return 3;
    }


    virtual ~BadJsonError() {

    }
};


#endif //APPSERVER_BADJSONERROR_H
