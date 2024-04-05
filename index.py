class Concepts : #class utile pour le 2e fichier qui contiendra les concepts et les QOS/Requirements/Business Rules
    def __init__(self,fichier2) -> None:
        self.probaConcept = {} #ça va etre un dictionnaire {ServiceID : [{Concept1:proba_associé},{Concept2:proba_associé}]}
        self.data = {} #on fera un dictionnaire {(event,serviceID) : [concept1,concept2...]}
        self.concepts = []
        self.ListeDeServiceID = []
        with open(fichier2,'r') as f : 
            datas = f.readlines()
            for line in datas : 
                tmp = line.strip().split(" : ")
                self.data[(tmp[0],tmp[1])] = [] #ATTENTION = marchera pas si on a un meme tuple (event,serviceID)
                if tmp[1] not in self.ListeDeServiceID : 
                    self.ListeDeServiceID.append(tmp[1])
                for i in range(2,len(line)) : 
                    self.data[(tmp[0],tmp[1])].append(tmp[i])
                    if tmp[i] not in self.concepts : 
                        self.concepts.append(tmp[i])
            #FIN DU FOR = on a extrait les informations lignes par ligne 

            for i in range(len(self.ListeDeServiceID)) : #dans ce FOR, on remplira la variable self.probaConcept
                self.probaConcept[self.ListeDeServiceID[i]] = []
                for concept in self.findConceptsWrtServiceID(self.ListeDeServiceID[i]) : 
                    self.probaConcept[self.ListeDeServiceID[i]].append({concept : self.proba(self.ListeDeServiceID[i],concept)})

    def proba(self,serviceID,concept) : #proba à associer à chaque concept selon le serviceID
        #formule de bayes dans le TER publication page 6
        self.nbExecutionsService = len(self.ListeDeServiceID)
        #self.copyConcepts = self.concepts.copy()
        self.NbApparitionConcept = 0
        for key in self.data : 
            if serviceID in key : 
                if concept in self.data[key] : 
                    self.NbApparitionConcept += 1
        return self.NbApparitionConcept//self.nbExecutionsService
    
    def findConceptsWrtServiceID(self,serviceID) : #fonction qui retourne une liste des concepts associé à un serviceID
        self.tmp = []
        for key in self.data : 
            if serviceID in key : 
                for concept in self.data[key] : 
                    if concept not in self.tmp : 
                        self.tmp.append(concept)
        return self.tmp

class assertions : #dans la ABOX, on aurait les concepts avec leur constante (proba associé) ex : Concept(0.5,serviceID)
    def __init__(self) -> None:
        self.ABOX = [] #utiliser la variable probaConcept mais juste un changement de syntaxe grosomodo -> [{Concept : (proba_associé,serviceID)}]
    
    def setABOX(self) : 
        pass

class Log : #class utile pour le 1er fichier log
    def __init__(self,fichierLog) -> None:
        self.event = {}
        self.serviceID = {}
        self.time = {}
        self.description = {}
        with open(fichierLog,"r") as f : 
            firstLine = f.readline().strip().split(" - ")
            self.time[firstLine[0]] = []
            self.event[firstLine[1]] = []
            self.serviceID[firstLine[2]] = []
            for i in range(3,len(firstLine)):
                self.description[firstLine[i]] = []
            data = f.readlines()
            del data[0]
            for line in data : 
                tmp = line.strip().split(" - ")
                self.time[firstLine[0]].append(tmp[0])
                self.event[firstLine[1]].append(tmp[1])
                self.serviceID[firstLine[2]].append(tmp[2])
                for i in range(3,len(line)):
                    self.description[line[i]].append(tmp[i])

