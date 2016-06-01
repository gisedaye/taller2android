//
// Created by fede on 4/3/16.
//

#ifndef APPSERVER_ENTITY_H
#define APPSERVER_ENTITY_H


#include <json/json.h>

using namespace std;
using namespace Json;

class Entity {

protected:

    virtual void fromJSON(Value value) = 0;

public:

    Entity();

    bool fetch();

    bool save();

    bool remove();

    virtual Value toJSON() = 0;

    /**
     * Returns this entity primary key
     */
    virtual string primaryKeyValue() = 0;

    /**
     * Works as key prefix in key,value rocksdb usage.
     */
    virtual string getName() = 0;

    virtual ~Entity();
};


#endif //APPSERVER_ENTITY_H
