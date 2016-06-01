#ifndef APPSERVER_FileLogger_H
#define APPSERVER_FileLogger_H

#include <iostream>
#include <fstream>
#include <ctime>
#include <sstream>
#include <iomanip>
#include "LockFile.h"

class FileLogger {

private:
    LockFile _output;
    unsigned short int _logLevel;
    struct tm* _currentTime;
    static FileLogger* _logger;

    FileLogger(const char* filename) : _output(filename), _logLevel(LOG_NOTICE), _currentTime(NULL) {};
    virtual ~FileLogger() {
        //delete _output;
    };

    static std::string prependCaller(const std::string& msg, const std::string& caller);

public:
    static const unsigned short int LOG_DEBUG = 1;
    static const unsigned short int LOG_NOTICE = 2;
    static const unsigned short int LOG_WARNING = 3;
    static const unsigned short int LOG_CRITICAL = 4;

    /*
     * filename: nombre del archivo a crear
     * logLevel: representa uno de los 4 niveles soportados por la clase
     * */
    static void initialize(const char* filename, unsigned short int logLevel) {
        // TODO: Ptr Error check
        if (!_logger) {
            _logger = new FileLogger(filename);
            _logger->_logLevel = logLevel;
        }
    };

    static void destroy() {
        if (_logger)
            delete _logger;
    }

    static void setLogLevel( unsigned short int logLevel) {
        _logger->_logLevel = logLevel;
    }

    /*
     * Loggea mensaje si y solo si el logLevel es mayor al setteado en la inicializacion
     * */
    static void log(const std::string& msg, unsigned short int logLevel);

    /*
     * Loggea mensaje si y solo si el logLevel es mayor al setteado en la inicializacion
     * Prefija el mensaje con el name de quien loguea
     * */
    static void log(const std::string& msg, unsigned short int logLevel, const std::string& caller);

    /*
     * Las siguientes funciones loguean el mensaje sólo si el logLevel seteado en la inicialización
     * es mayor a lo que indica el name de la función.
     * El segundo parámetro es prefijado ante el mensaje, si aparece
     * */
    static void debug(const std::string& msg);
    static void debug(const std::string& msg, const std::string& caller);
    static void info(const std::string &msg);
    static void info(const std::string &msg, const std::string &caller);
    static void warn(const std::string& msg);
    static void warn(const std::string& msg, const std::string& caller);
    static void error(const std::string& msg);
    static void error(const std::string& msg, const std::string& caller);

};

#endif //APPSERVER_FileLogger_H
