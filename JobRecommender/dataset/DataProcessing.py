import pandas
from model.Job import Job
import random
import datetime


def processJobDataset():
    headers = pandas.read_csv(
        "E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\datasets\\marketing_sample_for_naukri_com-jobs__2019.csv", nrows=1)

    df = pandas.read_csv(
        "E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\datasets\\marketing_sample_for_naukri_com-jobs__2019.csv",
        usecols=[col for col in headers if col != 'Uniq Id' and col != 'Crawl Timestamp'])

    df = df.drop(df[(df['Industry'] != 'IT-Software, Software Services') | (
            df['Role Category'] != 'Programming & Design') | (df['Job Experience Required'] == '')
                    | (df['Key Skills'] == '')].index)

    jobs = df.sample(1000)

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
    sec = 0
    for index, row in df.iterrows():
        techs = remodelTechs(row['Key Skills'])
        if techs == "nan" or techs == " " or techs == "":
            continue
        job = Job(index, str(row['Job Title']).strip(),
                  ""
                  ,
                  random.choices(["full-time", "internship", "part-time"], [0.8, 0.1, 0.1],
                                 k=1)[0], random.choice([True, False]),

                  classifyJobExperience(row['Job Experience Required']), random.randint(1000, 3500),
                  str(row['Role']),

                  techs, random.randint(1, 10), datetime.datetime.now() + datetime.timedelta(seconds=sec), None)
        sec += 1

        jobs.append(job)
    return jobs
