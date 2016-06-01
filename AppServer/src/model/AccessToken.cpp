//
// Created by fedefarina on 07/04/16.
//

#include "AccessToken.h"

AccessToken::AccessToken() {

}

void AccessToken::fromJSON(Value value) {
    this->username = value.get("username", "").asString();
}

string AccessToken::getName() {
    return "token/";
}

Value AccessToken::toJSON() {
    Value value;
    value["username"] = username;
    return value;
}

string AccessToken::primaryKeyValue() {
    return this->token;
}

const string &AccessToken::getToken() const {
    return token;
}

void AccessToken::setToken(const string &token) {
    this->token = token;
}

const string &AccessToken::getUsername() const {
    return username;
}

void AccessToken::setUsername(const string &username) {
    this->username = username;
}

AccessToken::~AccessToken() {

}
