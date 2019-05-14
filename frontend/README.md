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
* Avoir npm ou similaire d'installer en global
* Avoir un terminal
* Pour toute modification, un IDE est le bienvenu

### Installation:

#### Aller dans le dossier "/MDL/frontend/".
* "npm install" pour installer toutes les dépendances
* Si demandé, faire les installations requises en plus par npm

#### Lancer le frontend:
* "npm run serve" pour lancer le frontend
* Lancer votre navigateur (Chrome ou Firefox)
* Aller sur "localhost:8080/" pour accéder au site


## Le site (pages accessibles sans être connecté ou inscrit):
### La page d'accueil pour faire votre première recherche
* Accessilble directement à l'arrivée sur le site

### La page de résultat affichant les résultats de la recherche

* Accessible une fois une recherche faite via la page accueil ou la barre de recherche supérieure (présente sur toutes les pages sauf la page d'accueil)
* La page d'un article pour avoir un avant-goût de son contenu

* Lors d'une recherche, vous pouvez cliquer sur le titre d'un article pour aller sur la page de cet article et avoir plus d'informations sur le contenu de celui-ci
* Vous pouvez aller sur le site qui host l'article via le bouton se trouvant en bas à droite de l'abstract (ou en cliquant sur le lien dans la partie Info à droite)

### La page d'un State of the Art (SotA) pour avoir accès à ses informations

* Lors de votre recherche, dans l'onglet SotA, vous pouvez cliquer sur le titre d'un SotA pour avoir accès à sa page et avoir toutes ses informations
* L'onglet Visualisation montre un arbre resprésentant les différents domaines du SotA et les artcicles qui le composent
* Cliquer sur un point du graphe pour réduire ou agrandir une branche
* Cliquer sur un article pour y avoir accès
* Permet aussi de télécharger le bibtex du SotA

### La page d'un User pour avoir ses informations

* La page User vous donne plein d'informations utiles pour en savoir plus sur d'autres utilisateurs du site

## Le site (pages accessibles quand inscrit et connecté), toutes accessibles via le menu déroulant disponible lors d'un clic sur sa photo de profil en haut à droite:

##### Toutes les pages du site sont accessibles quand nous sommes inscrits et connectés. Nous avons également accès à toutes les fonctionnalités, par exemple la possibilité de bookmark un article ou SotA

### La page SotA-Helper
* Cette page a pour but d'aider à créer un SotA à partir des articles bookmarkés et d'ajouter des fichiers bibtex externes
* Elle permet aussi d'extraire le sota en .bib et .json
* La page dispose aussi d'un onglet Visualisation qui permet d'avoir une vue d'ensemble sur tout notre SotA grâce à un arbre
* Nous pouvons aussi mettre en ligne le SotA une fois celui-ci fini sur le site.

### La page modification de profil
* Permet de modifier son profil et d'ajouter des informations

## Les packages utilisés

   * "bibtex-parse-js": "0.0.24",
   * "bootstrap": "^4.3.1",
   * "bootstrap-vue": "^2.0.0-rc.13",
   * "color-hash": "^1.0.3",
   * "d3": "^5.9.2",
   * "swiper": "^4.5.0",
   * "vue": "^2.6.6",
   * "vue-awesome-swiper": "^3.1.3",
   * "vue-carousel": "^0.18.0",
   * "vue-d3-cloud": "^0.1.6",
   * "vue-material": "latest",
   * "vue-resource": "^1.5.1",
   * "vue-router": "^3.0.1",
   * "vued3tree": "^3.7.1",
   * "vuelidate": "^0.7.4"
