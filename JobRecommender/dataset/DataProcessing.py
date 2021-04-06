import math
from collections import Counter

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

        result = result[0:len(result) - 1]
        return result
    except Exception as e:
        print(e)
        result = "Any"
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
                  random.choices(["Full-time", "Internship", "Part-time"], [0.8, 0.1, 0.1],
                                 k=1)[0], bool(random.choices([True, False], [0.6, 0.4], k=1)),

                  classifyJobExperience(row['Job Experience Required']), 1000,
                  str(row['Role']),

                  remodelTechs(row['Key Skills']), 1, None)
        jobs.append(job)
    return saveToFile("../jobs.txt", jobs)


def saveToFile(fileName, jobs):
    with open(fileName, 'w') as f:
        for job in jobs:
            f.write(str(job))
            f.write('\n')

    f.close()
    return jobs


def readFromFile(fileName):
    jobs = []
    with open(fileName, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip().split('|')
            jobs.append(
                Job(int(line[0]), line[1], line[2], line[3], bool(line[4]), line[5], int(line[6]), line[7], line[8],
                    int(line[9]), None))
    f.close()
    for job in jobs:
        print(job)
    return jobs


def getFeatureVectorHeaders(jobs, input_id):
    vectorString = ""
    input_query = []
    for job in jobs:
        if job.id != input_id:
            vectorString += job.minExperience.strip() + "," + job.jobType.strip() + "," + job.devType.strip() + "," + str(
                job.remote).strip() + "," + job.techs.strip()
        else:
            input_query.append(job)

    vectorString = vectorString.lower()
    vector = vectorString.strip().split(",")
    vector = set(vector)
    vector = set(elem.strip() for elem in vector)

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
        vector = vectorString.split(',')
        vector = set(vector)
        vector = set(elem.strip() for elem in vector)
        vector.remove('')
        cnt1 = 0
        for index, label in enumerate(headers):
            for feature in vector:
                if feature == label:
                    empty[index] = 1
                    cnt1 += 1

        check.append((len(vector), cnt1))
        vectorString = ""
        vectors.append(empty)
    print(check)
    return vectors
