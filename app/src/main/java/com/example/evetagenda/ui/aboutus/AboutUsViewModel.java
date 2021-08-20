package com.example.evetagenda.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Καλώς ήρθατε στην eVetAgenda, την πρώτη Ελληνική εφαρμογή οργάνωσης και διαχείρισης κτηνιατρικών επαγγελμάτων. Το συγκεκριμένο app αναπτύχθηκε το 2021, ως διπλωματική εργασία του φοιτητή Mαρκέλ Πέγιο, της σχολής Μηχανικών Πληροφορικής και Τηλεπικοινωνιών, με έδρα την Κοζάνη.\n" +
                "\n" +
                "ΣΚΟΠΟΣ: Προσφέρουμε σε κτηνιάτρους σημαντικές, απλές στην χρήση, μη χρονοβόρες λειτουργίες, με σκοπό την καλύτερη και πιο αποτελεσματική οργάνωση της εργασίας τους. Δημιουργήστε τώρα τον προσωπικό σας λογαριασμό και:\n" +
                "\n" +
                " Οργανώστε εύκολα τους πελάτες σας και το ιστορικό τους\n" +
                " Αποκτήστε τον έλεγχο και τη διαχείριση των επισκέψεων σας\n" +
                " Αποκτήστε πρόσβαση στα ιατρικά αρχεία σας από οπουδήποτε\n" +
                " Εξοικονομήστε χρόνο");
    }

    public LiveData<String> getText() {
        return mText;
    }
}