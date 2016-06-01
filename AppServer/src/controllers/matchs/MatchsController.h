//
// Created by fedefarina on 17/04/16.
//

#ifndef APPSERVER_MATCHSCONTROLLER_H
#define APPSERVER_MATCHSCONTROLLER_H


#include "../BaseController.h"

class MatchsController : public BaseController {

public:
    MatchsController();

    void getMatches(Request &request, JsonResponse &response);

    void setup();

    virtual ~MatchsController();
};


#endif //APPSERVER_MATCHSCONTROLLER_H
