# Projet SIGN

## Membre du projet
- Corentin Hervochon
- Tom Ribardiere
- Ladislas Walcak

## Compte rendu du déroulé du Projet

Notre application Android est composée d'une simple Activity contenant un Webview. La librairy OpenLayers étant principalement en JavaScript, la partie Android est très simple. La seule subtilité étant la déclaration des permissions nécessaire dans le manifest (cf. AndroidManifest.xml), ainsi que l'activation de la géolocalisation.

Pour ce qui est de OpenLayers, tout est contenu dans un simple fichier HTML, définissant une carte OpenLayers ainsi que quelques commandes. La carte OpenLayers est une carte OSM centrée sur les coordonnées d'Orléans. De plus, grâce à une permission dans le manifeste, nous obtenont et affichant également la géolocalisation de l'appareil.

Pour les couches demandées dans les énoncés, nous avions à notre disposition un jeu de données sur https://data.orleans-metropole.fr/explore/?q=theme:Transports&sort=modified où nous avons sélectionné les jeux de données suivants:
- Aménagements cyclabes Orléans Métropole
- Stationnements vélos - TOPOS 2016 - Orlénans Métropole
- Lignes des bus du réseau TAO - Orléans Métropole
- Lignes de tram du réseau TAO - Orléans Métropole
- Arrêts des bus et du tram du réseau TAO - Orléans Métropole

Nous avons importé ses données sous forme de ShapeFile dans une base de données PostGIS (Tournant dans un docker, afin de pouvoir facilement isolé et déplacer cette base) à l'aide de QGIS (permettant de formatter les données sous forme de couche lisible par PostGIS).

Un second container Docker nous permet d'avoir un server Geoserver. La configuration est très simple et quasiment identique à celle réalisé lors des cours (Création d'un espace de travail, liaison avec PostGIS grâce à un entrepot, publication des couches, activation de WMS).

Une fois cette configuration faite, nous avons pû commencer à développer notre application.

Dans un premier temps, nous avons utilisé des TileWMS pour afficher les différentes couches. La récupération des données et l'affichage est extrêmement simple, grâce à la création de l'endpoint WMS. Cependant, nous avons ensuite eu pas mal de soucis pour mettre en place la sémiologie ainsi que plusieurs fonctionnalités. En effet, les Tiles étant des images pré-rendues par Geoserver, toutes modifications au sein de l'application est impossibles. Pour ce qui est de la création des légendes, nous avons dans un premier temps résolu le problèmes grâce à l'utilisation de style SLD (Styled Layer Description). Cependant, cette solution était très peu ergonomique (Nécessite de définir et appliquer tous les styles manuellement dans le Geoserver), et c'est pour cela que nous avons donc décidé de passer à une solution plus complète.

Nous sommes donc passé de TileWMS vers des sources de type Vector. Grâce à ceci, nous obtenons les données brutes grâce à nos requêtes, puis nous créons les Features coté client (OpenLayers se charge de automatiquement de créer les Features à partir des données de la source de la couche). De plus, cette méthode nous permet d'appliquer des styles à chaque type de Feature coté client, et de manière beaucoup plus simple. Nous générions dans un premier temps les styles aléatoirement, mais à cause de soucis de lisibilité dans certains cas, nous avons décidé de hardcodé les styles dans le fichier JS.

Ensuite nous nous sommes mis sur le filtre des lignes de tram et de bus que nous avons fait avec des requêtes CQL (passés en paramètre dans l'URL de la requête) en JavaScript.

Pour finir, nous avons utilisé un petit peu de JQuery afin de générer automatiquement les tableaux de légendes (Permettant de ne pas devoir tout ré-écrire dans la cas ou nous changerions les styles des couches).