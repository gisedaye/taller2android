//
// Created by fedefarina on 26/03/16.
//

#ifndef APPSERVER_ERRORS_H
#define APPSERVER_ERRORS_H

#include <string>

using namespace std;

class Error {

private:
    int code;

protected:
    string message;

public:

    Error() { }

    const string &getMessage() const {
        return message;
    }

    void setMessage(const string &message) {
        this->message = message;
    }

    virtual int getCode() = 0;


    virtual ~Error() { }
};


#endif //APPSERVER_ERRORS_H
