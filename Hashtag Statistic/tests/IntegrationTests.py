import unittest

from config import Config
from statistics.HashtagStatistics import HashtagStatistics

config = Config()
statistics = HashtagStatistics(config)
url = config.get_url()


class TestHashtagStatistics(unittest.TestCase):
    def test_correct_request(self):
        result = statistics.get_statistics(url, "VK", 10)
        self.assertIsNotNone(result)

    def test_negative_hours(self):
        with self.assertRaises(RuntimeError):
            statistics.get_statistics(url, "VK", -1)

    def test_incorrect_hours(self):
        with self.assertRaises(RuntimeError):
            statistics.get_statistics(url, "VK", 25)

    def test_empty_hashtag(self):
        with self.assertRaises(RuntimeError):
            statistics.get_statistics(url, "", 10)


if __name__ == '__main__':
    unittest.main()
