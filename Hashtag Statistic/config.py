import configparser


class Config:
    def __init__(self):
        self.__config = configparser.ConfigParser()
        self.__config.read("../settings.ini")

    def get_url(self):
        return self.__config.get("Settings", "url")

    def get_token(self):
        return self.__config.get("Settings", "token")

    def get_version(self):
        return self.__config.get("Settings", "version")
