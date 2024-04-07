import { Paper } from "@mui/material";
import Modal from "@mui/material/Modal";

export default function CustomModal({ isOpen, closeModal, children }) {
  return (
    <Modal open={isOpen} onClose={closeModal}>
      <Paper
        elevation={2}
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 800,
          maxHeight: "100%",
            padding: "10px",
        }}
      >
        {children}
      </Paper>
    </Modal>
  );
}
