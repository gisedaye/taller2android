//
// Created by federicofarina on 4/20/16.
//

#include "Database.h"

std::string DBPath = "/tmp/appServerDB";
Database* Database::s_instance= NULL;

Database *Database::getInstance() {
    if (s_instance == NULL)
        s_instance = new Database();
    return s_instance;
}

Database::Database() {
    Options options;
    // Optimize RocksDB. This is the easiest way to get RocksDB to perform well
    options.IncreaseParallelism();
    options.OptimizeLevelStyleCompaction();
    // create the DB if it's not already present
    options.create_if_missing = true;

    Status s = DB::Open(options, DBPath, &db);
    assert(s.ok());
    this->db = db;
}

void Database::destroy() {
    if (s_instance != NULL)
        delete (s_instance);
}

DB *Database::getDb() const {
    return db;
}
