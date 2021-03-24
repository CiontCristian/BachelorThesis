
class Job:
    def __init__(self, id, title, dev_type, job_type, min_experience, remote, techs):
        self.id = id
        self.title = title
        self.dev_type = dev_type
        self.job_type = job_type
        self.min_experience = min_experience
        self.remote = remote
        self.techs = techs

    def __str__(self):
        return str(self.id) + " " + str(self.title) + " " + str(self.dev_type) + " "+ str(self.job_type) + " " + str(self.min_experience) +" " + str(self.remote) +" " + str(self.techs)

