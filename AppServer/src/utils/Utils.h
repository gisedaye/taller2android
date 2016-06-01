#ifndef APPSERVER_UTILS_H
#define APPSERVER_UTILS_H

#include "vector"
#include "iostream"
#include "string.h"
using namespace std;

class Utils {
public:
    static string &arrayToString(vector<string>& array, string& result);
    static void stringToArray(string source, vector<string>& array);

};


#endif //APPSERVER_UTILS_H
