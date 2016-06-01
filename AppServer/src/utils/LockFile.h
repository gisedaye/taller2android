#ifndef APPSERVER_LOCKFILE_H
#define APPSERVER_LOCKFILE_H

# include <unistd.h>
# include <fcntl.h>
# include <string>

class LockFile {
private :

    struct flock fl;
    int fd;
    std :: string name;
public :
    LockFile(const std::string name);
    ~LockFile();

    int getLock();
    int releaseLock();
    ssize_t writeFile(const void *buffer, const ssize_t buffsize) const;
};

#endif //APPSERVER_LOCKFILE_H
