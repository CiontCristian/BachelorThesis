
import pandas
from model.Job import Job
import random


def processJobDataset():
    headers = pandas.read_csv(
        "E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\datasets\\marketing_sample_for_naukri_com-jobs__2019.csv", nrows=1)

    df = pandas.read_csv(
        "E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\datasets\\marketing_sample_for_naukri_com-jobs__2019.csv",
        usecols=[col for col in headers if col != 'Uniq Id' and col != 'Crawl Timestamp'])

    df = df.drop(df[(df['Industry'] != 'IT-Software, Software Services') | (
            df['Role Category'] != 'Programming & Design') | (df['Job Experience Required'] == '')].index)

    jobs = df.sample(50)

    return jobs


def classifyJobExperience(experience):
    dict = {2: "entry", 4: "junior", 7: "middle", 9: "senior", 11: "lead", 14: "manager"}
    try:
        experience = experience.strip('yrs').replace(" ", "").split('-')
        experience = list(map(int, experience))

        result = ""
        for key in dict.keys():
            if experience[0] <= key <= experience[1]:
                result += dict[key] + ","
            elif experience[0] > 14:
                result += dict[key] + ","
            elif experience[1] < 2:
                result = dict[2] + ","

        result = result[0:len(result) - 1]

        return result
    except Exception as e:
        print(e)
        result = "entry,junior,middle,senior,lead,manager"
        print("Exception " + result)
        return result


def remodelTechs(techs):
    techs = str(techs)
    techs = techs.strip().replace("|", ",").split(",")
    strip_techs = ""
    for tech in techs:
        tech = tech.strip()
        strip_techs += tech + ","
    strip_techs = strip_techs[0:len(strip_techs) - 1]
    return strip_techs


def cleanDataset():
    df = processJobDataset()
    jobs = []
    for index, row in df.iterrows():
        job = Job(index, str(row['Job Title']), "Desc1",
                  random.choices(["full-time", "internship", "part-time"], [0.8, 0.1, 0.1],
                                 k=1)[0], random.choice([True, False]),

                  classifyJobExperience(row['Job Experience Required']), 1000,
                  str(row['Role']),

                  remodelTechs(row['Key Skills']), 1, None)
        jobs.append(job)
    return jobs


def getFeatureVectorHeaders(jobs, input_id):
    vectorString = ""
    input_query = []
    for job in jobs:
        vectorString += job.minExperience.strip() + "," + job.jobType.strip() + "," + job.devType.strip() + "," + str(
            job.remote).strip() + "," + job.techs.strip()
        if job.id == input_id:
            input_query.append(job)

    vectorString = vectorString.lower()
    vector = vectorString.strip().split(",")
    print(len(vector))
    vector = set(vector)
    vector = set(elem.strip() for elem in vector)
    print(len(vector))
    return transformJobsToFeatureVectors(vector, jobs), transformJobsToFeatureVectors(vector, input_query)


def transformJobsToFeatureVectors(headers, jobs):
    vectors = []
    vectorString = ""
    check = []

    for job in jobs:
        empty = [0 for _ in range(len(headers))]
        vectorString += job.minExperience.strip() + ", " + job.jobType.strip() + ", " + job.devType.strip() + ", " + str(
            job.remote).strip() + ", " + job.techs.strip() + ", "

        vectorString = vectorString.lower()
        vectorString = vectorString[0:len(vectorString) - 2]
        vector = vectorString.split(',')
        vector = set(vector)
        vector = set(elem.strip() for elem in vector)
        # vector.remove('')
        cnt1 = 0
        for index, label in enumerate(headers):
            for feature in vector:
                # print(feature + " " + label)
                if feature == label:
                    empty[index] = 1
                    cnt1 += 1

        check.append((len(vector), cnt1))
        vectorString = ""
        vectors.append(empty)
    print(check)
    return vectors
