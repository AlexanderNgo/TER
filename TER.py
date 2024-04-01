import re
from owlready2 import *
import datetime

def creer_fichier_log(fichier) : # POUR CREER UN FICHIER LOG A PARTIR DE .TXT SI PB DE CREATION
    with open(fichier, 'r') as fichier_texte:
        with open('Log.log', 'w') as fichier_journal:
            for ligne in fichier_texte:
                horodatage = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                niveau_gravite = "INFO" #on laisse à INFO pour le test
                fichier_journal.write(f"{horodatage} - {niveau_gravite} - {ligne}")

    print("Conversion terminée. Votre fichier journal a été créé avec succès.")

#owl = Ontology("D:\\COURS_M1\\M1_TER\\onto.owl")

class Evenement(Thing):
    pass

class Horodata(DataProperty):
    domain = [Evenement]
    range = [str]

class EventType(DataProperty):
    domain = [Evenement]
    range = [str]

class description(DataProperty) : 
    domain = [Evenement]
    range = [str]

def lire_fichier_journal(nom_fichier):
    with open(nom_fichier, 'r') as f:
        lignes = f.readlines()
    return lignes

def extraire_informations_ligne(ligne):
    # Utilisation d'expressions régulières pour extraire les informations
    pattern = r'(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}) - (\w+) - (.*)'  # Pattern pour une ligne au format YYYY-MM-DD HH:MM:SS EVENTTYPE Description (pour l'instant)
    match = re.match(pattern, ligne)
    if match:
        date_heure = match.group(1) #type = str
        print(type(date_heure))
        eventtype = match.group(2)
        message = match.group(3)
        return date_heure, eventtype, message
    else:
        return None, None, None

def creer_individus_fichier_journal(lignes):
    for ligne in lignes:
        date_heure, eventtype, message = extraire_informations_ligne(ligne)
        if date_heure:
            evenement = Evenement()
            evenement.horodata.append(date_heure)
            evenement.eventType.append(eventtype)
            evenement.description.append(message)

nom_fichier_journal = "Log.log" 
lignes_journal = lire_fichier_journal(nom_fichier_journal)
creer_individus_fichier_journal(lignes_journal)
#creer_fichier_log("Test.txt")

#owl.save("ontologie.owl")
