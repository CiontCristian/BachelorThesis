class Preference:
    def __init__(self, user_id, job_id, interested, applied):
        self.user_id = user_id
        self.job_id = job_id
        self.interested = interested
        self.applied = applied

    def __str__(self):
        return str(self.user_id) + " " + str(self.job_id) + " " + str(self.interested) + " " + str(self.applied)
