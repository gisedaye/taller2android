add_executable(AccountTest model/AccountTest.cpp ${SOURCES} main.cpp model/EntityTest.cpp model/EntityTest.h)
target_link_libraries(AccountTest ${LIBS_NAMES})

add_executable(EntityTest ${SOURCES} main.cpp)
target_link_libraries(EntityTest ${LIBS_NAMES})

add_test(NAME "EntityTest" COMMAND EntityTest)
add_test(NAME "AccountTest" COMMAND AccountTest)
