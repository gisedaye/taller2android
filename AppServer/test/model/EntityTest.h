//
// Created by federicofarina on 4/26/16.
//

#ifndef APPSERVER_ENTITYTEST_H
#define APPSERVER_ENTITYTEST_H

#include <cppunit/extensions/HelperMacros.h>
#include "../../src/model/Account.h"

using namespace CPPUNIT_NS;

class EntityTest : public TestFixture {
CPPUNIT_TEST_SUITE(EntityTest);
        CPPUNIT_TEST(fetched);
        CPPUNIT_TEST(notFetched);
        CPPUNIT_TEST(removed);
        CPPUNIT_TEST(notRemoved);
    CPPUNIT_TEST_SUITE_END();

private:

    Account testEntity;

public:
    EntityTest();

    void fetched();

    void notFetched();

    void removed();

    void notRemoved();

    virtual void setUp() override;

    virtual void tearDown() override;;

    virtual ~EntityTest();


};


#endif //APPSERVER_ENTITYTEST_H
