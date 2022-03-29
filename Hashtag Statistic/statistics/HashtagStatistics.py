import requests
from datetime import datetime
import time


class HashtagStatistics:
    def __init__(self, config):
        self.config = config

    def get_statistics(self, url, hashtag, hours):
        try:
            hours = int(str(hours))
        except ValueError:
            raise RuntimeError('Hours must be an integer')
        if not (1 <= hours <= 24):
            raise RuntimeError('Hours must be in range [1; 24]')
        if not hashtag:
            raise RuntimeError('Hashtag must not be empty')
        result = []
        date = datetime.now()
        unix_time = time.mktime(date.timetuple())
        for i in range(0, hours):
            response = self.__make_request(url, hashtag, unix_time - 3600, unix_time)
            result.append(response)
            unix_time -= 3600
        result.reverse()
        return result

    def __make_request(self, url, hashtag, start, end):
        token = self.config.get_token()
        version = self.config.get_version()
        response = requests.get(url, params={
            'count': 0,
            'v': version,
            'access_token': token,
            'q': '#' + hashtag,
            'start_time': start,
            'end_time': end
        })
        if response:
            print(response.json())
            if 'error' in response.json():
                raise RuntimeError('An error occurred during request. ' + response.json()['error']['error_msg'])
            else:
                return response.json()['response']['total_count']
        else:
            raise RuntimeError('Failed to make request.')
