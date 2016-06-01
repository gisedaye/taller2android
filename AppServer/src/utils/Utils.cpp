#include "Utils.h"

string& Utils::arrayToString(vector <string> &array, string& result) {
    for (unsigned int i = 0; i < array.size(); i++) {
        result.append(array[i] + ",");
    }
    return result;
}

void Utils::stringToArray(string source, vector<string>& array) {
    std::string delimiter = ",";
    size_t pos = 0;
    std::string token;
    while ((pos = source.find(delimiter)) != std::string::npos) {
        token = source.substr(0, pos);
        array.push_back(token);
        std::cout << token << std::endl;
        source.erase(0, pos + delimiter.length());
    }
}

