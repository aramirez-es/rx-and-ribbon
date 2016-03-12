from locust import HttpLocust, TaskSet, task
from faker import Factory

fake = Factory.create()

class LoadTest(TaskSet):
    @task()
    def displayThePtaForm(self):
        data = {
            'user': fake.name(),
            'latitude': fake.latitude(),
            'longitude': fake.longitude(),
            'itemId': fake.random_digit_not_null()
        }
        with self.client.get(
                "/hello",
                params=data,
                catch_response=True) as response:
            if response.status_code == 200:
                response.success()



class UserThatVisitsPta(HttpLocust):
    host = "http://localhost:8000"
    task_set = LoadTest
    min_wait = 1000
    max_wait = 3000
