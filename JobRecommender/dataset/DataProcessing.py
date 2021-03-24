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
        job = Job(index, str(row['Job Title']), str(row['Role']),
                  random.choices(["Full-time", "Internship", "Part-time"], [0.8, 0.1, 0.1],
                                 k=1)[0],
                  classifyJobExperience(row['Job Experience Required']),
                  bool(random.choices([True, False], [0.6, 0.4], k=1)),
                  remodelTechs(row['Key Skills']))
        jobs.append(job)
    return jobs


def getFeatureVectorHeaders(jobs):
    vectorString = ""
    for job in jobs:
        vectorString += job.min_experience.strip() + "," + job.job_type.strip() + "," + job.dev_type.strip() + "," + str(
            job.remote).strip() + "," + job.techs.strip()

    vectorString = vectorString.lower()
    vector = vectorString.strip().split(",")
    vector = set(vector)

    return transformJobsToFeatureVectors(vector, jobs)


def transformJobsToFeatureVectors(headers, jobs):
    vectors = []
    vectorString = ""
    for job in jobs:
        empty = [0 for _ in range(len(headers))]
        vectorString += job.min_experience + ", " + job.job_type + ", " + job.dev_type + ", " + str(
            job.remote) + ", " + job.techs
        vector = vectorString.split(',')
        vector = set(vector)
        for index, label in enumerate(headers):
            for feature in vector:
                if feature == label:
                    empty[index] = 1
        vectors.append(empty)
    return vectors
