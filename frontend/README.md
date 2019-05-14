# PROJET MDL

# frontend

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn run serve
```

### Compiles and minifies for production
```
yarn run build
```

### Run your tests
```
yarn run test
```

### Lints and fixes files
```
yarn run lint
```

### Run your end-to-end tests
```
yarn run test:e2e
```

### Run your unit tests
```
yarn run test:unit
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).



## Installation et lancement:

### Avant intallation:
* Avoir npm ou similaire d'installer en global.
* Avoir un terminal.
* Pour toute modification, un IDE est le bienvenu.

### Installation:

#### Aller dans le dossier "/MDL/frontend/".
* "npm install" pour installer toutes les dépendances.
* Si demandé, faire les installations requise en plus par npm

#### Lancer le frontend:
* "npm run serve" pour lancer le frontend.
* Lancer votre navigateur (Chrome ou Firefox).
* Aller sur "localhost:8080/" pour accéder au site.


## Le site (page accèssible sans être connecté ou inscrit):
### La page d'acceuil pour faire votre première recherche.
* Accèssilble directement à l'arrivée sur le site.

### La page de résultats affichant les résultats de la recherche.

* Accèssible une fois une recherche faite via la page acceuil ou la barre de recherche supérieure.
* La page d'un article pour voire un avant goût de son contenu.

* Lors d'une recherche, vous pouvez cliquer sur le titre d'un article pour arriver sur cette page et avoir plus d'information sur le contenu de celui-ci.
* Vous pouvez aller sur le site qui host l'article via le bouton se trouvant prêt de l'abstract.

### La page d'un State of the Art (SotA) pour avoir accès à ses informations.

* Lors de votre recherche, dans l'onglet SotA, vous pouvez cliquer sur le titre d'un SotA pour avoir accès à cette page et avoir toutes les informations sur le sota.
* L'onglet visualisation montre un arbre resprésentant les différents domaines du SotA et les artcicles qui les composent.
* Cliquer sur un point du graph pour réduire ou grandire une branche.
* Cliquer sur un article pour y avoir accès.
* Permet aussi de telecharger le bibtex du SotA.

### La page d'un User pour avoir les informations sur celui-ci.

* La page user vous donne pleins d'informations utiles pour en savoir plus sur d'autres utilisateurs du site.
* Toutes les pages sont accèssible quand nous sommes inscrit et connecté. Par exemple la possibilité de bookmark un article ou SotA

## Le site (page accèssible quand inscrit et connecté), toutes accèssible via le menu déroulant disponible lors d'un clique sur sa photo de profile en haut à droite.

### La page SotA-helper
* Cette page à pour but d'aider à créer un SotA à partir des articles bookmarké et de rajouter des fichiers bibtex externes
* Elle permet aussi d'extraire le sota en .bib et .json.
* La page dispose aussi d'une page visualisation qui permet d'avoir une vue d'ensemble de tout notre SotA grace à un arbre.
* Nous pouvons aussi mettre en ligne le SotA une fois celui-ci fini sur le site.

### La page modification de profile
* Permet de modifier son profile et de rajouter des informations

## Les packages utilisés

* Vue.js
* Vue-Material
* Bootstrap
* D3.js
