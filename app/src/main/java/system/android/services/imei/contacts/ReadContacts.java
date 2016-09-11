package system.android.services.imei.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import system.android.services.imei.contacts.models.ModelContact;
import system.android.services.imei.contacts.models.ModelEmail;

import java.util.ArrayList;

/**
 * Created by dave on 25/6/16.
 */
public class ReadContacts {
	static ContentResolver cr;
	static Cursor contactsCursor;
	public static ArrayList<ModelContact> getContactsList(Context context) {
		ArrayList<ModelContact> contacts = new ArrayList<>();

		ContentResolver cr = context.getContentResolver();
		contactsCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		if ( isContactListEmpty(contactsCursor)==false) {
			ModelContact contact;
			while ( contactsCursor.moveToNext() ) {
				contact=getNextContact(cr,contactsCursor);
				if(contact!=null)
				contacts.add(contact);
			}
		}
		return contacts;

	}

	private static Boolean isContactListEmpty(Cursor contactsCursor){
		if(contactsCursor.getCount() > 0)
			return false;
		return true;
	}
	private static ModelContact getNextContact(ContentResolver cr,Cursor contactsCursor){

		String id = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
		String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

		if ( Integer.parseInt(contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0 ) {
			// get the phone number
			Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[]{id}, null);
			ArrayList<String> phoneNumberList=new ArrayList<>();
			while ( pCur.moveToNext() ) {
				String phone = pCur.getString(
						pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				phoneNumberList.add(phone);
			}
			pCur.close();

			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
					new String[]{id}, null);
			ArrayList<ModelEmail> emailList=new ArrayList<>();
			ModelEmail emailModal;
			while ( emailCur.moveToNext() ) {
				String email = emailCur.getString(
						emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				String emailType = emailCur.getString(
						emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

				emailModal=new ModelEmail(email,emailType);
				emailList.add(emailModal);
			}
			emailCur.close();

			return  new ModelContact(name,phoneNumberList,emailList);
		}
		return null;
	}

}
