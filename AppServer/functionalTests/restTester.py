import unittest
from testCases import AccountTest

if __name__ == '__main__':
	suite = unittest.TestSuite()

	tests = unittest.TestLoader().loadTestsFromTestCase(AccountTest)
	suite.addTests(tests)

	unittest.TextTestRunner().run(suite)

