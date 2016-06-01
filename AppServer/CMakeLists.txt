# If not required version present, finalize execution
cmake_minimum_required(VERSION 2.8 FATAL_ERROR)

# Set project name and path
set(PROJECT_NAME "appServer")
project(${PROJECT_NAME} C CXX)


set(ROOT_PATH "${${PROJECT_NAME}_SOURCE_DIR}")
# Libs headers folder
set(LIBS_PATH "${ROOT_PATH}/lib")
# SRC folder
set(SRC_PATH "${ROOT_PATH}/src")
set(ROCKSDB_INCLUDE_DIRS "/usr/include/")
set(CPPUNIT_ROOT_DIR "${ROOT_PATH}/lib/cppunit/")

# Tests SRC folder
set(TEST_PATH "${ROOT_PATH}/test/")


# - use ISO C++11 required for RocksDB
#  pthread is required by gtest.
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread -std=c++11 ")

# Nombres de las librerías a usar en el proyecto
set(LIBS_NAMES rocksdb z snappy bz2 jsoncpp mongoose cppunit)

# Chequeo la existencia de todas las librerias agregadas
message(STATUS "*** Check for libs existence ***")

foreach (LIB ${LIBS_NAMES})
    find_library(LIB{LIB} NAMES ${LIB})
    if (LIB{LIB}) #False si es 0, OFF, NO, FALSE, N, IGNORE, NOTFOUND, vacio, o si termina en "-NOTFOUND". Caso contrario es TRUE.
        message(STATUS "Lib |${LIB}| found!")
    else (LIB${LIB})
        message(FATAL_ERROR "|${LIB}| lib not found.")
    endif (LIB{LIB})
endforeach (LIB ${LIBS_NAMES})

set(MONGOOSE_CPP "${PROJECT_SOURCE_DIR}/lib/mongoose")
set(SHA256_CPP "${PROJECT_SOURCE_DIR}/lib/sha256")

set(SOURCES
        ${SOURCES}
        ${MONGOOSE_CPP}/Utils.cpp
        ${MONGOOSE_CPP}/Controller.cpp
        ${MONGOOSE_CPP}/Mutex.cpp
        ${MONGOOSE_CPP}/mongoose.c
        ${MONGOOSE_CPP}/Request.cpp
        ${MONGOOSE_CPP}/Response.cpp
        ${MONGOOSE_CPP}/Server.cpp
        ${MONGOOSE_CPP}/Session.cpp
        ${MONGOOSE_CPP}/Sessions.cpp
        ${MONGOOSE_CPP}/StreamResponse.cpp
        ${MONGOOSE_CPP}/UploadFile.cpp
        ${MONGOOSE_CPP}/WebController.cpp
        ${MONGOOSE_CPP}/JsonResponse.cpp
        ${MONGOOSE_CPP}/JsonController.cpp
        ${MONGOOSE_CPP}/WebSocket.cpp
        ${MONGOOSE_CPP}/WebSockets.cpp
        ${SHA256_CPP}/sha256.cpp
        ${SRC_PATH}/errors/UsernameAlreadyInUseError.h
        ${SRC_PATH}/model/AccessToken.cpp
        ${SRC_PATH}/controllers/matchs/MatchsController.cpp
        ${SRC_PATH}/errors/UnauthorizedError.h
        ${SRC_PATH}/utils/FileLogger.h
        ${SRC_PATH}/utils/LockFile.h
        ${SRC_PATH}/utils/FileLogger.cpp
        ${SRC_PATH}/utils/LockFile.cpp
        ${SRC_PATH}/db/Database.cpp
        ${SRC_PATH}/db/Database.h
        ${SRC_PATH}/utils/Utils.cpp
        ${SRC_PATH}/utils/Utils.h
        ${SRC_PATH}/controllers/accounts/AccountController.cpp
        ${SRC_PATH}/controllers/accounts/AccountController.h
        ${SRC_PATH}/controllers/BaseController.cpp
        ${SRC_PATH}/controllers/BaseController.h
        ${SRC_PATH}/errors/Error.h
        ${SRC_PATH}/errors/EmptyParamError.h
        ${SRC_PATH}/errors/BadJsonError.h
        ${SRC_PATH}/model/Entity.cpp
        ${SRC_PATH}/model/Entity.h
        ${SRC_PATH}/model/Account.cpp
        ${SRC_PATH}/model/Account.h)


include_directories("${MONGOOSE_CPP}")
include_directories("${CPPUNIT_ROOT_DIR}")

# Compiling executable
message(STATUS "*** Generating executable ***")
add_executable(${PROJECT_NAME} ${SOURCES} ${SRC_PATH}/main.cpp)

include_directories("${ROCKSDB_INCLUDE_DIRS}")

#Include library path for compiler
include_directories(${LIBS_PATH})
target_link_libraries(${PROJECT_NAME} ${LIBS_NAMES})

# Tests
enable_testing()
add_subdirectory(${TEST_PATH})






