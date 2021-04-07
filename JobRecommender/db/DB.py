import psycopg2

from model.Job import Job


class DB:
    def __init__(self):
        self.con = psycopg2.connect(database="bachelor", user="postgres", password="1234", host="127.0.0.1",
                                    port="5432")
        self.jobs = []

    def getJobs(self):
        if len(self.jobs) == 0:
            cur = self.con.cursor()
            cur.execute("SELECT * from job")
            rows = cur.fetchall()

            for row in rows:
                self.jobs.append(Job(row[0], row[9], row[2], row[4], row[7], row[6], row[5], row[3], row[8], row[1], None))

        return self.jobs

