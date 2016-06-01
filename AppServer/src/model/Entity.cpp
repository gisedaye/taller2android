//
// Created by fede on 4/3/16.
//

#include "Entity.h"
#include "../db/Database.h"

Entity::Entity() {

}

bool Entity::fetch() {
    string value;
    DB *db = Database::getInstance()->getDb();

    Status s = db->Get(ReadOptions(), getName() + primaryKeyValue(), &value);
    bool fetched = s.ok();

    if (fetched) {
        Json::Reader reader;
        Value json;
        reader.parse(value, json);
        fromJSON(json);
    }
    return fetched;
}

bool Entity::save() {
    DB *db = Database::getInstance()->getDb();
    Status s = db->Put(WriteOptions(), getName() + primaryKeyValue(), toJSON().toStyledString());
    return s.ok();
}

bool Entity::remove() {
    DB *db = Database::getInstance()->getDb();
    Status s = db->Delete(WriteOptions(), getName() + primaryKeyValue());
    return s.ok();
}

Entity::~Entity() {
}