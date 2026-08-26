import { NotificationType } from './enums';

// Matches NotificationResponse.java exactly. `read` is correct as-is: the
// backend field is `private boolean isRead;`, and since the field name
// already starts with "is", Lombok generates isRead() (not isIsRead()) and
// Jackson's bean-property stripping turns that into the JSON key "read".
// `type` is the real domain NotificationType enum (TRANSACTION_SUCCESS,
// KYC_VERIFIED, GENERAL, ...) — not the generic INFO/WARNING/ALERT/SUCCESS
// values this previously declared, which never appear in real responses.
// `linkUrl` does not exist on the backend DTO and has been removed.
export interface NotificationResponse {
  id: number;
  type: NotificationType;
  title: string;
  message: string;
  read: boolean;
  referenceId?: string;
  referenceType?: string;
  createdAt: string;
}
