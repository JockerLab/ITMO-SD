from unittest import TestCase, main
from unittest.mock import patch, Mock

from config import Config
from statistics.HashtagStatistics import HashtagStatistics

data = [12, 5, 9, 9, 8, 6, 13, 13, 6, 5]


def get_pseudo_data(count):
    result = []
    for i in range(0, count):
        result.append(data[i % len(data)])
    return result


class TestHashtagStatistics(TestCase):
    @patch('statistics.HashtagStatistics')
    def get_statistics(self, hashtag, hours, MockStatisctics):
        config = Config()
        statistics = MockStatisctics(config)
        url = config.get_url()
        statistics.get_statistics.return_value = get_pseudo_data(hours)
        return statistics.get_statistics(url, hashtag, hours)

    def test_statistics(self):
        for hours in range(1, 25):
            expected = get_pseudo_data(hours)
            actual = self.get_statistics("VK", hours)
            self.assertIsNotNone(actual)
            self.assertEqual(actual, expected, "Incorrect answer")


if __name__ == '__main__':
    main()
