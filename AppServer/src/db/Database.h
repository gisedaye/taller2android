//
// Created by federicofarina on 4/20/16.
//

#ifndef APPSERVER_DATABASE_H
#define APPSERVER_DATABASE_H

#include "rocksdb/db.h"

using namespace rocksdb;

class Database {

private:

    DB *db;

    int m_value;
    static Database *s_instance;

    Database();

    virtual ~Database() {
        //delete _output;
    };

public:

    static Database *getInstance();

    static void destroy();

    DB *getDb() const;
};


#endif //APPSERVER_DATABASE_H
