import sys

from config import Config
from statistics.HashtagStatistics import HashtagStatistics


if __name__ == "__main__":
    if len(sys.argv) == 3:
        try:
            config = Config()
            statistics = HashtagStatistics(config)
            url = config.get_url()
            print(statistics.get_statistics(url, sys.argv[1], sys.argv[2]))
        except RuntimeError as e:
            print(e)
    else:
        print("Incorrect number of arguments.")
