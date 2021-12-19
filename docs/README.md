Alexis YVON, Nicolas MARINHO  ; g2_10

# Accès au serveur

Repo : https://github.com/nicomr70/dilemme-prisonnier

- Se placer dans le répertoire racine
- Lancer la commande `mvn clean install`
- Lancer la commande `heroku local`

# Accès au client

Repo : https://github.com/nicomr70/dilemme-prisonnier-front

- Se placer dans le répertoire racine
- Lancer la commande `npm install`
- Lancer la commande `npm start`

# Accès à l'interface strategie  partagée et les strategies partagées

* IStrategy : https://github.com/Lxsvn98/prisoners-dilemma-shared-strategy.git
* strategies partagées à l'autre groupe : https://github.com/nicomr70/prisoners-dilemma-strategies.git

# Résumé

Le serveur SpringBoot en Java garde en mémoire les parties du jeu du **Dilemme du prisonnier**.

Celui-ci communique avec des clients React en JavaScript sur la base de requêtes HTTP et d'émissions SSE.

Pour simuler un échange entre deux joueurs, il suffit d'ouvrir deux onglets ou fenêtre dans le navigateur à l'adresse `localhost:3000`.

Le jeu n'est malheureusement pas entièrement fonctionnel pour le moment.


# Déploiement

le serveur et le client sont déployé sur les adresses suivantes :

* serveur : https://dilemme-back.herokuapp.com
* client : https://dilemme-prisonnier.herokuapp.com

# Diagramme de composant

vous trouveez un digramme de composant dans le serveur au path suivant : ``/docs/SharedProject.png``

# Note

* sur sonnar , notre projet est ne passe plus a cause de duplicate code, mais cela n'est pas normal car le code dupliqués correspond à implementation de l'interface IStrategy

