//
// Created by federicofarina on 4/26/16.
//

#include "AccountTest.h"
#include "../../src/model/Account.h"

CPPUNIT_TEST_SUITE_REGISTRATION(AccountTest);

AccountTest::AccountTest() {

}


void AccountTest::setUp() {
    testAccount.setUsername("anUsername");
    testAccount.setPassword("aPassword");
    testAccount.addKeepAccount("1");
    testAccount.addKeepAccount("2");
    testAccount.addKeepAccount("3");
    testAccount.addTossAccount("4");
    testAccount.addTossAccount("5");
    testAccount.save();
}


void AccountTest::toJSON() {

    const Value &value = testAccount.toJSON();

    //Tests that all fields are serialized in json result
    CPPUNIT_ASSERT(!value.get("userId", "no empty").empty());
    CPPUNIT_ASSERT(!value.get("username", "no empty").empty());
    CPPUNIT_ASSERT(!value.get("password", "no empty").empty());
    CPPUNIT_ASSERT(!value.get("keptAccounts", "no empty").empty());
    CPPUNIT_ASSERT(!value.get("tossedAccounts", "no empty").empty());
}

void AccountTest::fromJSON() {
    Account account;
    account.setUsername("anUsername");
    account.fetch();
    CPPUNIT_ASSERT(account.getUsername() == "anUsername");
    CPPUNIT_ASSERT(account.getPassword() == "aPassword");

    const vector<string> &keptAccounts = account.getKeptAccounts();

    CPPUNIT_ASSERT(keptAccounts.size() == 3);
    CPPUNIT_ASSERT(keptAccounts.at(0) == "1");
    CPPUNIT_ASSERT(keptAccounts.at(1) == "2");
    CPPUNIT_ASSERT(keptAccounts.at(2) == "3");


    const vector<string> &tossedAccounts = account.getTossedAccounts();
    CPPUNIT_ASSERT(tossedAccounts.size() == 2);
    CPPUNIT_ASSERT(tossedAccounts.at(0) == "4");
    CPPUNIT_ASSERT(tossedAccounts.at(1) == "5");
}


void AccountTest::tearDown() {
    testAccount.remove();
}


AccountTest::~AccountTest() {

}




