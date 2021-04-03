
class Job:
    def __init__(self, id, title, description, jobType, remote, minExperience, minCompensation, devType, techs, availablePos, contractor):
        self.id = id
        self.title = title
        self.description = description
        self.jobType = jobType
        self.remote = remote
        self.minExperience = minExperience
        self.minCompensation = minCompensation
        self.devType = devType
        self.techs = techs
        self.availablePos = availablePos
        self.contractor = contractor

    def serialize(self):
        return {
            'id': self.id,
            'title': self.title,
            'description': self.description,
            'jobType': self.jobType,
            'remote': self.remote,
            'minExperience': self.minExperience,
            'minCompensation': self.minCompensation,
            'devType': self.devType,
            'techs': self.techs,
            'availablePos': self.availablePos,
            'contractor': None
        }

    def __str__(self):
        return str(self.id) + " " + str(self.title) + " " + str(self.devType) + " " + str(self.jobType) + " " + str(self.minExperience) + " " + str(self.remote) + " " + str(self.techs)

