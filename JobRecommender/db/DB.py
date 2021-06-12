import psycopg2

from model.Job import Job
from model.Preference import Preference


class DB:
    def __init__(self):
        self.con = psycopg2.connect(database="bachelor", user="postgres", password="1234", host="127.0.0.1",
                                    port="5432")
        self.jobs = []
        self.unseen_jobs = []
        self.preferences = []
        self.background = []

    def getJobs(self):

        cursor = self.con.cursor()
        cursor.execute("SELECT * from job")
        rows = cursor.fetchall()

        self.jobs.clear()
        for row in rows:
            self.jobs.append(
                Job(row[0], row[10], row[3], row[5], row[8], row[7], row[6], row[4], row[9], row[1], row[2], None))

        return self.jobs

    def getJobById(self, id):

        cursor = self.con.cursor()
        cursor.execute("SELECT * from job where id=" + str(id))
        rows = cursor.fetchall()
        job = None

        for row in rows:
            job = Job(row[0], row[10], row[3], row[5], row[8], row[7], row[6], row[4], row[9], row[1], row[2], None)
            break

        return job

    def getRatedJobsCBF(self, input_id):
        cursor = self.con.cursor()
        cursor.execute("select id from job j inner join preference p on p.job_id=j.id where p.user_id="+str(input_id)+"and (p.interested=TRUE or p.interested=FALSE or p.relevance_main=FALSE)")

        rows = cursor.fetchall()

        ids = []
        for row in rows:
            ids.append(row[0])

        return ids

    def getRatedJobsKNN(self, input_id):
        cursor = self.con.cursor()
        print(input_id)
        cursor.execute("select id from job j inner join preference p on p.job_id=j.id where p.user_id="+str(input_id)+"and (p.relevance_sec=FALSE)")

        rows = cursor.fetchall()

        ids = []
        for row in rows:
            ids.append(row[0])

        return ids

    def getUserPreferences(self, id):
        cursor = self.con.cursor()
        cursor.execute("SELECT * from preference where user_id=" + str(id))

        rows = cursor.fetchall()

        self.preferences.clear()
        for row in rows:
            self.preferences.append(Preference(row[3], row[2], row[1], row[0], row[4], row[5]))

        return self.preferences

    def getUserBackground(self, id):
        cursor = self.con.cursor()
        cursor.execute(
            "SELECT * from background b inner join generic_user gu on b.id = gu.background_id where gu.id =" + str(
                id))

        rows = cursor.fetchall()

        self.background.clear()
        for row in rows:
            self.background = [row[1], row[2], row[3], row[4], row[5]]

        return self.background
