#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <mongoose/Server.h>
#include "controllers/accounts/AccountController.h"
#include "controllers/matchs/MatchsController.h"
#include "utils/FileLogger.h"
#include "db/Database.h"

using namespace std;
using namespace Mongoose;

volatile static bool running = true;

void handle_signal(int sig) {
    if (running) {
        cout << "Exiting..." << endl;
        running = false;
    }
}

int main() {
    srand(time(NULL));

    signal(SIGINT, handle_signal);

    FileLogger::initialize("server.out", FileLogger::LOG_DEBUG);

    Server server(8083);

    AccountController accountController;
    server.registerController(&accountController);

    MatchsController matchsController;
    server.registerController(&matchsController);


    server.setOption("enable_directory_listing", "false");
    server.start();

    cout << "Server started, routes:" << endl;
    accountController.dumpRoutes();
    matchsController.dumpRoutes();

    while (running) {
        sleep(10);
    }

    Database::destroy();
    FileLogger::destroy();

    server.stop();
    return EXIT_SUCCESS;
}
