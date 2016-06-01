import requests
import unittest
import json
import uuid
import ConfigParser

class AccountTest(unittest.TestCase):
	def __init__(self, *args, **kwargs):
		super(AccountTest, self).__init__(*args, **kwargs)
		self.__api_base_url = "http://localhost:8083/"
		self._url_usuario = "/api/accounts/"
		

	def test_1registrar_usuario(self):
		payload = {'username': "'"+ uuid.uuid4().hex +"'",'password':'nuevo'}
		r = requests.post(self.__api_base_url + self._url_usuario + "signup", json=payload)
		self.assertEqual(r.status_code, 200)
		data = json.loads(r.text)
		self.assertEqual(data["data"]["message"], "Successful signup")