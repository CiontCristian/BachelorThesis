import psycopg2

from model.Job import Job
from model.Preference import Preference


class DB:
    def __init__(self):
        self.con = psycopg2.connect(database="bachelor", user="postgres", password="1234", host="127.0.0.1",
                                    port="5432")
        self.jobs = []
        self.preferences = []
        self.background = []

    def getJobs(self):
        if len(self.jobs) == 0:
            cursor = self.con.cursor()
            cursor.execute("SELECT * from job")
            rows = cursor.fetchall()

            for row in rows:
                self.jobs.append(Job(row[0], row[10], row[3], row[5], row[8], row[7], row[6], row[4], row[9], row[1], row[2], None))

        return self.jobs

    def getUserPreferences(self, id):
        if len(self.preferences) == 0:
            cursor = self.con.cursor()
            cursor.execute("SELECT * from preference where user_id=" + str(id))

            rows = cursor.fetchall()

            for row in rows:
                self.preferences.append(Preference(row[3], row[2], row[1], row[0]))

        return self.preferences

    def getUserBackground(self, id):
        if len(self.background) == 0:
            cursor = self.con.cursor()
            cursor.execute("SELECT * from background b inner join generic_user gu on b.id = gu.background_id where gu.id =" + str(id))

            rows = cursor.fetchall()

            for row in rows:
                self.background = [row[1], row[2], row[3], row[4], row[5]]

        return self.background
