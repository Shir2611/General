/// <summary>
/// Console.WriteLine
/// Console.ReadLine()
/// Creating Enumerands (enum)
/// Enum.Parse
/// Enum.ToString
/// float.Parse
/// float.ToString
/// [Flags] attribute
/// string concatenation
/// string.Format
/// Verbatim String (@)
/// Numeric string formatting
/// </summary>
namespace FirstVSApp
{
    [System.Flags]// This enables the enum to work with bitwise operations.
    public enum eWeekDays
    {
        Sunday = 1,
        Monday = 2,
        Tuesday = 4,
        Wednesday = 8,
        Thursday = 16,
        Friday = 32,
        Saturday = 64
    }

    class Program
    {
        public static void Main()
        {
            System.Console.WriteLine("Hi. Please enter your name (and then press enter):");
            // simple ReadLine operation:
            string name = System.Console.ReadLine();

            System.Console.WriteLine("Please enter your prefered working days (and then press enter):");
            // creating an enum from a string:
            string weekDayStr = System.Console.ReadLine();
            eWeekDays workingDay = (eWeekDays)System.Enum.Parse(typeof(eWeekDays), weekDayStr);

            System.Console.WriteLine("Please enter your prefered salary (and then press enter):");
            // creating an int from a string:
            string salaryStr = System.Console.ReadLine();

            float salary = 0;
            bool goodInput = float.TryParse(salaryStr, out salary); // returns false upon failure
            if (goodInput)
            {
                salary *= 1.1f;
            }

            // string concatenation:
            string msg = "Hi " + name + "!\n";
            msg = msg + "You are working on " + workingDay + ".\n";
            msg += "You are going to get " + salary + " shekel an hour :)\n";

            // string formatting: 
            msg = string.Format(
                "Hi {0}!\nYou are working on {1}.\nYou are going to get {2:c} an hour :)\nBye {1}!",
                name, workingDay, salary);

            // Environment.NewLine :
            msg = string.Format(
                "Hi {1}!{0}You are working on {2}.{0}You are going to get {3:c} an hour :){0}Bye {1}!",
                System.Environment.NewLine, name, workingDay, salary);

            // Verbatim string ("@"):
            msg =
                string.Format(
@"Hi {0}!
You are working on {1}.
You are going to get {2:C} an hour :)
Bye {0}!",
                name, workingDay, salary);

            // the program's output:
            System.Console.WriteLine(msg);

            SpeechLib.SpVoice voice = new SpeechLib.SpVoice();
            voice.Speak(msg, SpeechVoiceSpeakFlags.SVSFDefault);

            // wait for enter
            System.Console.WriteLine("Please press 'Enter' to exit...");
            System.Console.ReadLine();
        }
    }
}
