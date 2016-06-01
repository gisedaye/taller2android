//
// Created by fede on 4/3/16.
//

#include "Account.h"

Account::Account() {
    this->matches.empty();
    this->keptAccounts.empty();
    this->tossedAccounts.empty();
}

Account::Account(const string &username) : username(username) {
    this->matches.empty();
    this->keptAccounts.empty();
    this->tossedAccounts.empty();
}

Value Account::toJSON() {
    Value value;
    std::string s1, s2, s3;
    value["username"] = username;
    value["password"] = password;
    value["matches"] = Utils::arrayToString(this->matches, s1);
    value["keptAccounts"] = Utils::arrayToString(this->keptAccounts, s2);
    value["tossedAccounts"] = Utils::arrayToString(this->tossedAccounts, s3);
    return value;
}

void Account::fromJSON(Value value) {
    this->keptAccounts.clear();
    this->tossedAccounts.clear();
    this->username = value.get("username", "").asString();
    this->password = value.get("password", "").asString();
    Utils::stringToArray(value.get("matches", "").asString(), this->matches);
    Utils::stringToArray(value.get("keptAccounts", "").asString(), this->keptAccounts);
    Utils::stringToArray(value.get("tossedAccounts", "").asString(), this->tossedAccounts);
}

string Account::primaryKeyValue() {
    return username;
}

string Account::getName() {
    return "account/";
}



void Account::setUsername(const string &username) {
    Account::username = username;
}

const string &Account::getUsername() const {
    return username;
}

const string &Account::getPassword() const {
    return password;
}

void Account::setPassword(const string &password) {
    this->password = password;
}

void Account::addKeepAccount(const string &keptAccount) {
    this->keptAccounts.push_back(keptAccount);
    Account otherAccount(keptAccount);
    if (otherAccount.fetch()) {
        vector<string> otherKepts = otherAccount.getKeptAccounts();
        for (std::vector<string>::iterator it = otherKepts.begin() ; it != otherKepts.end(); ++it) {
            if (*it == this->username) {
                this->addMatch(keptAccount);
                otherAccount.addMatch(this->username);
                otherAccount.save();
            }
        }
    }
}

void Account::addTossAccount(const string &tossedAccount) {
    this->tossedAccounts.push_back(tossedAccount);
}

Account::~Account() {
    this->matches.clear();
    this->keptAccounts.clear();
    this->tossedAccounts.clear();
}

const vector<string> &Account::getMatches() const {
    return matches;
}

const vector<string> &Account::getTossedAccounts() const {
    return tossedAccounts;
}

const vector<string> &Account::getKeptAccounts() const {
    return keptAccounts;
}

void Account::addMatch(const string &match) {
    this->matches.push_back(match);
}
