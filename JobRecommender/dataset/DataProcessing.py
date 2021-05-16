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
            df['Role Category'] != 'Programming & Design') | (df['Job Experience Required'] == '')].index)

    jobs = df.sample(200)

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
        job = Job(index, str(row['Job Title']).strip(),
                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec facilisis eros purus, id varius erat pretium quis."
                  " Donec lobortis elit at euismod laoreet. Nam finibus augue vel posuere scelerisque. Maecenas eu augue iaculis, ultrices lacus nec,"
                  " porta ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla blandit erat mi, non accumsan orci rutrum a. Maecenas laoreet "
                  "imperdiet diam et cursus. Morbi feugiat orci ut magna condimentum dignissim.\nMorbi consequat libero vitae lorem pulvinar finibus. "
                  "Ut tempor risus sed congue interdum. Nunc fermentum, augue nec consequat finibus, leo dolor tincidunt nibh, quis porta turpis sem sit amet mi."
                  " Nunc id dolor pellentesque felis facilisis auctor. Nulla nec ante at sapien rhoncus malesuada ac eget velit. "
                  "Nam sit amet dignissim felis, sed cursus enim. Nunc interdum erat non ante auctor, nec ultricies nulla congue. "
                  "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean eget ligula quis libero dignissim facilisis. "
                  "Ut semper suscipit lacus rhoncus cursus. Sed pulvinar nec sapien non ultrices. Donec orci justo, tristique vitae leo sed, auctor euismod nulla. "
                  ,
                  random.choices(["full-time", "internship", "part-time"], [0.8, 0.1, 0.1],
                                 k=1)[0], random.choice([True, False]),

                  classifyJobExperience(row['Job Experience Required']), random.randint(1000, 3500),
                  str(row['Role']),

                  remodelTechs(row['Key Skills']), random.randint(1, 10), datetime.datetime.now(), None)
        jobs.append(job)
    return jobs
