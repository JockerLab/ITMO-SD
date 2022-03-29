import json
from unittest import TestCase

import requests
from stubserver import StubServer

from config import Config
from statistics.HashtagStatistics import HashtagStatistics

config = Config()
statistics = HashtagStatistics(config)


class StubTest(TestCase):
    def setUp(self):
        self.server = StubServer()
        self.server.run()

    def tearDown(self):
        self.server.stop()

    def test_correct_request(self):
        expected = {"response": {"items": [], "count": 7, "total_count": 7}}
        response = json.dumps({"response": {"items": [], "count": 7, "total_count": 7}})
        self.server.expect(method="GET", url="/newsfeed.search?.+$") \
            .and_return(mime_type="application/json", content=response)

        url = "http://localhost:8080/newsfeed.search?q=#VK"
        actual = requests.get(url).json()
        self.assertEqual(actual, expected, "Incorrect answer")
