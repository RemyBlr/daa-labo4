# Questions complémentaires

>6.1 Quelle est la meilleure approche pour sauver, même après la fermeture de l’app, le choix de
l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant
le code mettant en œuvre votre approche.

La bonne approche c'est d'utiliser des **SharedPreferences**. C'est une simple représentation clé/valeur
qui est tout à fait adapté pour cette simple donnée. Les SharedPreferences sont persisté et restent
disponibles même après fermeture de l'application.

Room n'est pas nécessaire car la donnée est simple et n'a pas de relation ou une structure complexe *(String)*.

Exemple dans le ViewModel,
```kotlin
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)
    private val currentSortType = MutableLiveData<String>()

    init {
        currentSortType.value = settingsManager.getSortType()
    }

    fun updateSortType(newSort: String) {
        currentSortType.value = newSort
        settingsManager.saveSortType(newSort)
    }
}
```

A chaque redémarrage, l’app récupère le dernier choix.

>6.2 L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est-
ce que cette solution présente des limites ? Si oui, quelles sont-elles et quelles seraient les
approches mieux adaptées ?

LiveData c'est bien pour afficher une liste qui change en temps réel, mais il y a quand même des limites.
Déjà, il n'y a pas de gestion d'erreur intégrée - si la liste est vide, on ne sait pas si c'est parce
qu'il y a vraiment rien dans la base ou si y a eu un problème lors de la récupération. Ensuite, même
si Room exécute les requêtes en arrière-plan, LiveData reste assez limité pour les opérations asynchrones
complexes. Si l'on veut combiner plusieurs sources de données ou faire des transformations plus poussées,
c'est pas l'idéal.

Comme solution, les coroutines avec Flow seraient mieux adaptées. Flow permet de gérer les erreurs
proprement et offre plus de flexibilité pour les opérations asynchrones, tout en restant réactif comme LiveData.

>6.3 Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment
procéderiez-vous si vous souhaitiez proposer une interface permettant de sélectionner une
note pour l’éditer ?

On ajoute un listener dans l'adapter sur l'évenement onClick du ViewHolder,
```kotlin
class NoteAdapter(
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    // ...

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.setOnClickListener {
                onNoteClick(note)
            }
        }
    }
}
```