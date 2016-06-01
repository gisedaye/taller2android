#include <iostream>
#include "FileLogger.h"

FileLogger* FileLogger::_logger = NULL;

void FileLogger::log(const std::string& msg, unsigned short int logLevel) {
	if (logLevel >= _logger->_logLevel) {
		time_t timer;
		time(&timer);
		_logger->_currentTime = localtime(&timer);
		std::ostringstream oss;
		oss << std::setfill('0')
			<< std::setw(2) << _logger->_currentTime->tm_mday << "/"
			<< std::setw(2) << _logger->_currentTime->tm_mon + 1 << "/"
			<< _logger->_currentTime->tm_year + 1900 << " "
			<< std::setw(2) << _logger->_currentTime->tm_hour << ":"
			<< std::setw(2) << _logger->_currentTime->tm_min << ":"
			<< std::setw(2) << _logger->_currentTime->tm_sec << " "
			<< msg << "\n";
		_logger->_output.getLock();
		_logger->_output.writeFile((const void *) oss.str().c_str(), (long int) oss.str().size());
		// Comentar esto cuando haga falta
		std::cout << oss.str() << std::flush;
		_logger->_output.releaseLock();
	}
}

std::string FileLogger::prependCaller(const std::string& msg, const std::string& caller) {
	return "[ " + caller + " ] " + msg;
}

void FileLogger::log(const std::string& msg, unsigned short int logLevel, const std::string& caller) {
	log( prependCaller(msg, caller), LOG_DEBUG );
}

void FileLogger::debug(const std::string& msg) {
	log( msg, LOG_DEBUG );
}

void FileLogger::debug(const std::string& msg, const std::string& caller) {
	log( msg, LOG_NOTICE, caller );
}

void FileLogger::info(const std::string &msg) {
	log( msg, LOG_NOTICE );
}

void FileLogger::info(const std::string &msg, const std::string &caller) {
	log( msg, LOG_WARNING, caller );
}

void FileLogger::warn(const std::string& msg) {
	log( msg, LOG_WARNING );
}

void FileLogger::warn(const std::string& msg, const std::string& caller) {
	log( msg, LOG_WARNING, caller );
}

void FileLogger::error(const std::string& msg) {
	log( msg, LOG_CRITICAL );
}

void FileLogger::error(const std::string& msg, const std::string& caller) {
	log( msg, LOG_CRITICAL, caller );
}