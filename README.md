# ENSIS'AR

Ensis-Ar est une application mobile qui utilise la technologie ARcore.
Elle permet de guider les visiteurs de l'école sur le troisième étage.

## Fonctionnalités

* Augmentation d'information : les noms des salles sont affichées en grand sur un panneau au dessus des salles.
* Guidage : à l'aide d'une liste déroulante contennant les salles, le visiteur est guidé vers la salle choisie.
* Mini-jeux : les QR-code permettent de lancer des mini-jeux.

## Démo :

<video width="320" height="240" controls>
  <source src="video.mp4" type="video/mp4">
</video>


## Gestion de projet :

### Déroulement du projet 

### Répartion des taches



## Fonctionnement :
  Nous avons choisi de poser nos salles statiquement en fonction d'un point de départ. Toutes les salles sont donc placé dès le démarage de l'application. Au démarage de l'application le monde virtuel se génère et une ancre a la position (0,0,0) se place à la position actuelle du téléphone. Pour améliorer l'orientation du monde virtuel nous faisons l'initialisation en deux points.

### Partie blender

### Créations des pancartes

### Partie Android Studio

#### Visite

##### Intégration des pancartes
 Une fois les modèles 3D réalisés, il faut les ajouter dans l'application. Tous les modèles 3D se trouvent dans le dossier : [models](ENSISAR/app/sampledata/models) au format fbx. Il faut ensuite les convertir en sfb. Il faut rajouter des lignes du type :
 `sceneform.asset('sampledata/models/E30.fbx',
        'src/main/res/raw/e30')`

Celà permet de créer le .sfb dans le dossier raw qui est donc accessible dans le code à l'aide de R.raw. .

Il faut ensuite créer dans le code un objet capable du rendu : un [ModelRenderable](ENSISAR/app/src/main/java/com/example/ensisar/EnsisarVisit.java#L187).
On possède alors un objet 3D. Il nous faut maintenant le placer dans le monde.

Pour celà, on utilise des [nodes](https://developers.google.com/ar/reference/java/sceneform/reference/com/google/ar/sceneform/Node) qui permettent de placer des objets à des positions définies.
Nous avons désactiver les ombres car le rendu était trompeur, et rendait la lecture des pancartes difficile. Voici un extrait de la création de nos noeuds :

``` java
this.node.setParent(parent);
   if (model != null){
        model.setShadowReceiver(false);
        model.setShadowCaster(false);
        this.node.setRenderable(model);
    }
    this.node.setWorldPosition(position);
    this.node.setLocalScale(scale);
    this.node.setWorldRotation(rotation);
```

La créations des objets 3d se fait au contact de l'écran tactile  




##### Guidage
  Dès que quelques salles ont été mise en place, nous nous sommes attaqués à la fonctionalités de guidage. Pour ce faire, toutes les salles sont stockée dans une liste. Chaque salle possède une liste de salles voisines.

  Dès que l'utilisateur choisi une salle dans la liste, l'application va chercher le noeud ou la salle la plus proche et y mettre son point de départ. Elle va ensuite cherché la salle et renvoyer une liste de point pour ce diriger vers celle-ci. Une fois la liste de point acquise, il ne reste plus qu'à tracer des lignes vers la destination.


#### Mini-jeux


## Créateurs

* LONG LAURA
* LOISON GUILLEM
* KURZ FABIEN
* KEBOURI HAMZA
* JUMEAUX MAXIME
* WEBER ARTHUR
* HERR NICOLAS
